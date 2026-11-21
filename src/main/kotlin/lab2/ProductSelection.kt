package lab2

fun selectProducts(
    products: List<Product>,
    recommendedIds: List<Long>,
    excludedIds: List<Long>
): List<Product> {
    val result: MutableList<Product> = mutableListOf()
    val hashgrouped = products.associateByTo(HashMap()) { it.id }
    val recommendedSet = recommendedIds.toCollection(LinkedHashSet())
    val excludedSet = excludedIds.toHashSet()
    for (currentId in recommendedSet)
    {
        val product = hashgrouped[currentId] ?: continue
        if(!product.available) continue
        if(product.id in excludedSet) continue
        result.add(product)
    }
    return result
}