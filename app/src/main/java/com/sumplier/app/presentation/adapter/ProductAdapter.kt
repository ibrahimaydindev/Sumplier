package com.sumplier.app.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.data.model.Product
import com.sumplier.app.databinding.ProductItemBinding
import com.sumplier.app.presentation.popUp.QuantityPopup

class ProductAdapter(
    private var productList: List<Product>,
    private val onAddToCartClick: (Product, Double) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = productList.size

    inner class ProductViewHolder(private val binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.textViewProductName.text = product.productName
            binding.textViewProductPrice.text = "₺ ${product.price}"
            //Glide.with(binding.imageViewProduct.context).load(product.imageUrl).into(binding.imageViewProduct)

            // Normal tıklama - varsayılan miktar 1.0
            binding.productCard.setOnClickListener { 
                onAddToCartClick(product, 1.0)
            }

            // Uzun tıklama - miktar popup'ı
            binding.productCard.setOnLongClickListener { 
                showQuantityPopup(product)
                true
            }
        }

        private fun showQuantityPopup(product: Product) {
            val quantityPopup = QuantityPopup().apply {
                setOnQuantitySelectedListener { quantity ->
                    onAddToCartClick(product, quantity)
                }
            }
            quantityPopup.show((binding.root.context as AppCompatActivity).supportFragmentManager, "QuantityPopup")
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        productList = emptyList()
        productList = (newProducts)
        notifyDataSetChanged()
    }
}
