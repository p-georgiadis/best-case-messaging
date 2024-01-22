package io.milk.products

class ProductService(private val dataGateway: ProductDataGateway) {
    fun findAll(): List<ProductInfo> {
        return dataGateway.findAll().map { ProductInfo(it.id, it.name, it.quantity) }
    }

    fun findBy(id: Long): ProductInfo? {
        val record = dataGateway.findBy(id)
        return record?.let { ProductInfo(it.id, it.name, it.quantity) }
    }

    fun update(purchase: PurchaseInfo): ProductInfo? {
        val record = dataGateway.findBy(purchase.id)
        if (record != null) {
            record.quantity -= purchase.amount
            dataGateway.update(record)
            return ProductInfo(record.id, record.name, record.quantity)
        }
        return null
    }

    fun decrementBy(purchase: PurchaseInfo): ProductInfo? {
        dataGateway.decrementBy(purchase)
        val updatedRecord = dataGateway.findBy(purchase.id)
        return updatedRecord?.let { ProductInfo(it.id, it.name, it.quantity) }
    }
}
