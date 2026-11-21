package lab2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ProductSelectionTest {
    @Test
    fun selectsAvailableProductsInRecommendationOrder() {
        val keyboard = Product(1, "Keyboard", true)
        val mouse = Product(2, "Mouse", false)
        val monitor = Product(3, "Monitor", true)
        val webcam = Product(4, "Webcam", true)
        val products = listOf(keyboard, mouse, monitor, webcam)

        val result = selectProducts(
            products,
            listOf(3L, 2L, 3L, 99L, 1L, 4L),
            listOf(1L)
        )

        assertEquals(listOf(monitor, webcam), result)
    }

    @Test
    fun returnsEmptyListWhenThereAreNoRecommendations() {
        val products = listOf(Product(1, "Keyboard", true))

        val result = selectProducts(products, emptyList(), emptyList())

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun preservesRecommendationOrderRegardlessOfCatalogOrder() {
        val first = Product(1, "First", true)
        val second = Product(2, "Second", true)
        val third = Product(3, "Third", true)

        val result = selectProducts(
            listOf(third, first, second),
            listOf(2L, 3L, 1L),
            emptyList()
        )

        assertEquals(listOf(second, third, first), result)
    }

    @Test
    fun removesDuplicateRecommendations() {
        val keyboard = Product(1, "Keyboard", true)
        val monitor = Product(2, "Monitor", true)

        val result = selectProducts(
            listOf(keyboard, monitor),
            listOf(2L, 2L, 1L, 2L),
            emptyList()
        )

        assertEquals(listOf(monitor, keyboard), result)
    }

    @Test
    fun ignoresDuplicateAndUnknownExclusions() {
        val keyboard = Product(1, "Keyboard", true)
        val monitor = Product(2, "Monitor", true)

        val result = selectProducts(
            listOf(keyboard, monitor),
            listOf(1L, 2L),
            listOf(1L, 1L, 999L)
        )

        assertEquals(listOf(monitor), result)
    }

    @Test
    fun skipsUnavailableAndUnknownProducts() {
        val keyboard = Product(1, "Keyboard", false)
        val monitor = Product(2, "Monitor", true)

        val result = selectProducts(
            listOf(keyboard, monitor),
            listOf(99L, 1L, 2L),
            emptyList()
        )

        assertEquals(listOf(monitor), result)
    }

    @Test
    fun returnsEmptyListForEmptyCatalog() {
        val result = selectProducts(emptyList(), listOf(1L, 2L), emptyList())

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun includesAllRecommendedAvailableProductsWhenExclusionsAreEmpty() {
        val keyboard = Product(1, "Keyboard", true)
        val monitor = Product(2, "Monitor", true)

        val result = selectProducts(
            listOf(keyboard, monitor),
            listOf(2L, 1L),
            emptyList()
        )

        assertEquals(listOf(monitor, keyboard), result)
    }

    @Test
    fun returnsEmptyListWhenNoProductsMatch() {
        val unavailable = Product(1, "Mouse", false)
        val excluded = Product(2, "Keyboard", true)

        val result = selectProducts(
            listOf(unavailable, excluded),
            listOf(1L, 2L, 99L),
            listOf(2L)
        )

        assertEquals(emptyList<Product>(), result)
    }

    @Test
    fun doesNotModifyInputLists() {
        val keyboard = Product(1, "Keyboard", true)
        val monitor = Product(2, "Monitor", true)
        val products = mutableListOf(keyboard, monitor)
        val recommendations = mutableListOf(2L, 1L, 2L)
        val exclusions = mutableListOf(1L)
        val originalProducts = products.toList()
        val originalRecommendations = recommendations.toList()
        val originalExclusions = exclusions.toList()

        val result = selectProducts(products, recommendations, exclusions)

        assertEquals(listOf(monitor), result)
        assertEquals(originalProducts, products)
        assertEquals(originalRecommendations, recommendations)
        assertEquals(originalExclusions, exclusions)
    }

    @Test
    fun repeatedCallsHaveIndependentResults() {
        val keyboard = Product(1, "Keyboard", true)
        val monitor = Product(2, "Monitor", true)
        val products = listOf(keyboard, monitor)

        val first = selectProducts(products, listOf(1L, 2L), listOf(2L))
        val second = selectProducts(products, listOf(2L, 1L), emptyList())

        assertEquals(listOf(keyboard), first)
        assertEquals(listOf(monitor, keyboard), second)
        assertEquals(listOf(keyboard), first)
    }
}
