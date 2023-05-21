package com.example.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.test.domain.models.BasketItem
import com.example.test.domain.models.Category
import com.example.test.domain.models.Dish
import com.example.test.domain.models.Tag
import com.example.test.domain.usecases.UseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    useCases: UseCases,
) : ViewModel() {

    private val _dishList: MutableLiveData<List<Dish>> by lazy {
        MutableLiveData<List<Dish>>(listOf())
    }
    val dishList: LiveData<List<Dish>> = _dishList

    private val _searchList: MutableLiveData<List<Dish>> by lazy {
        MutableLiveData<List<Dish>>(listOf())
    }
    val searchList: LiveData<List<Dish>> = _searchList

    private val _filteredDishList: MutableLiveData<List<Dish>> by lazy {
        MutableLiveData<List<Dish>>(listOf())
    }
    val filteredDishList: LiveData<List<Dish>> = _filteredDishList

    private val _basketList: MutableLiveData<List<BasketItem>> by lazy {
        MutableLiveData<List<BasketItem>>(listOf())
    }
    val basketList: LiveData<List<BasketItem>> = _basketList

    private val _filtersList: MutableLiveData<List<Tag>> by lazy {
        MutableLiveData<List<Tag>>(listOf())
    }
    val filtersList: LiveData<List<Tag>> = _filtersList

    private val _checkedTagsList: MutableLiveData<List<Int>> by lazy {
        MutableLiveData<List<Int>>(listOf())
    }
    val checkedTagsList: LiveData<List<Int>> = _checkedTagsList

    private val _discountOnly: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val discountOnly: LiveData<Boolean> = _discountOnly

    private val _searchText: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val searchText: LiveData<String> = _searchText

    private val _categories: MutableLiveData<List<Category>> by lazy {
        MutableLiveData<List<Category>>(listOf())
    }
    val categories: LiveData<List<Category>> = _categories

    private val _currentPrice: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    val currentPrice: LiveData<Int> = _currentPrice

    private val _currentCategory: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>(0)
    }
    val currentCategory: LiveData<Int> = _currentCategory

    private val _currentCategoryDishes: MutableLiveData<List<Dish>> by lazy {
        MutableLiveData<List<Dish>>(listOf())
    }

    init {
        viewModelScope.launch {
            _categories.value = useCases.getCategoriesUseCase.invoke()
            _filtersList.value = useCases.getTagsUseCase.invoke()
            _dishList.value = useCases.getProductsUseCase.invoke()
        }
        _filteredDishList.value = _dishList.value
        _filteredDishList.value = _dishList.value
        onCategoryClick(_categories.value!!.first().id)
    }

    fun addToBasket(dish: Dish) {
        if (_basketList.value != null) {
            val oldBasketItem = _basketList.value!!.firstOrNull { it.id == dish.id }
            if (oldBasketItem != null) {
                val oldBacketItemIndex = _basketList.value!!.indexOf(oldBasketItem)
                _currentPrice.value = _currentPrice.value!! + dish.price_current
                _basketList.value = buildList {
                    addAll(_basketList.value!!.subList(0, oldBacketItemIndex))
                    add(oldBasketItem.copy(count = oldBasketItem.count + 1))
                    addAll(_basketList.value!!.subList(oldBacketItemIndex+1, _basketList.value!!.size))
                }
            } else {
                _currentPrice.value = _currentPrice.value!! + dish.price_current
                _basketList.value = buildList {
                    addAll(_basketList.value!!)
                    add(BasketItem(dish.id, 1))
                }
            }
        }
    }

    fun removeFromBasket(dish: Dish) {
        if (_basketList.value != null) {
            val oldBasketItem = _basketList.value!!.firstOrNull { it.id == dish.id }
            if (oldBasketItem != null) {
                _currentPrice.value = _currentPrice.value!! - dish.price_current
                if (oldBasketItem.count > 1) {
                    val oldBasketItemIndex = _basketList.value!!.indexOf(oldBasketItem)
                    _basketList.value = buildList {
                        addAll(_basketList.value!!.subList(0, oldBasketItemIndex))
                        add(oldBasketItem.copy(count = oldBasketItem.count - 1))
                        addAll(_basketList.value!!.subList(oldBasketItemIndex+1, _basketList.value!!.size))
                    }
                }
                else {
                    _basketList.value = _basketList.value!!.filter { it != oldBasketItem }
                }
            } else {
                return
            }
        }
    }

    fun checkTag(
        newValue: Boolean,
        tagId: Int
    ) {
        if (newValue) {
            _checkedTagsList.value = buildList {
                addAll(_checkedTagsList.value!!)
                add(tagId)
            }
        } else {
            _checkedTagsList.value = _checkedTagsList.value!!.filter { it != tagId }
        }
    }

    fun checkDiscountTag(
        newValue: Boolean
    ) {
        _discountOnly.value = newValue
    }

    fun onSearchTextChanged(newText: String) {
        _searchText.value = newText
        if (newText.isEmpty()) {
            _searchList.value = listOf()
            return
        }
        val dishesFromSearch = _dishList.value!!.filter {
            it.name.contains(newText, true) ||
                    it.description.contains(newText, true)
        }
        _searchList.value = dishesFromSearch
    }

    fun onCategoryClick(tagId: Int) {
        if (_currentCategory.value == tagId)
            return
        _currentCategory.value = tagId
        _currentCategoryDishes.value = _dishList.value!!.filter { it.category_id == tagId }
        _filteredDishList.value = _currentCategoryDishes.value
        if (!_checkedTagsList.value.isNullOrEmpty()) {
            onFiltersChanged()
        }
    }

    fun onFiltersChanged() {
        if (_checkedTagsList.value.isNullOrEmpty()) {
            if (_discountOnly.value == true)
                _filteredDishList.value =
                    _currentCategoryDishes.value!!.filter { it.price_old != null }
            else
                _filteredDishList.value = _currentCategoryDishes.value
            return
        }
        val filteredDish = _currentCategoryDishes.value!!.filter {
            it.tag_ids.isNotEmpty()
        }.filter {
            it.tag_ids.containsAll(_checkedTagsList.value!!)
        }

        if (_discountOnly.value == true)
            _filteredDishList.value = filteredDish.filter { it.price_old != null }
        else
            _filteredDishList.value = filteredDish
    }

}