package com.sumplier.app.presentation.activity.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumplier.app.data.model.Product
import com.sumplier.app.databinding.ProductItemBinding

class ProductAdapter(
    private var productList: List<Product>,
    private val onAddToCartClick: (Product) -> Unit
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

            //binding.buttonAddToCart.setOnClickListener { onAddToCartClick(product) }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateProducts(newProducts: List<Product>) {
        productList = emptyList()
        productList = (newProducts)
        notifyDataSetChanged()
    }
}
