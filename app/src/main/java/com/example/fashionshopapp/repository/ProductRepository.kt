import com.example.fashionshopapp.api.RetrofitInstance
import com.example.fashionshopapp.models.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await

class ProductRepository {
    suspend fun fetchProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getProducts().execute()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    suspend fun fetchProductsOnSale(): List<Product> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.getProductsOnSale().execute()
                if (response.isSuccessful) {
                    response.body() ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                emptyList()
            }
        }
    }

}
