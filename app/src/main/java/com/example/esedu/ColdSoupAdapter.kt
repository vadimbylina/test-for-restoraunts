package com.example.esedu

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class ColdSoupAdapter(var colds: List<ColdSoup>, var context: Context) : RecyclerView.Adapter<ColdSoupAdapter.MyViewHolder>()
{
    class  MyViewHolder(view:View) : RecyclerView.ViewHolder(view)
    {
        var image: ImageView = view.findViewById(R.id.coldslistimage)
        var name: TextView = view.findViewById(R.id.coldslisttitle)
        var comp: TextView = view.findViewById(R.id.coldslistcomp)
        var price: TextView = view.findViewById(R.id.coldslistprice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_colds_in_list, parent,false)
        return  ColdSoupAdapter.MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return colds.count()
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = colds[position].name
        holder.comp.text = colds[position].composition
        holder.price.text = colds[position].price.toString() + " руб"
        val imageIdentifier = colds[position].image

        // Проверяем, содержится ли в идентификаторе изображения префикс "file://"
        if (imageIdentifier.startsWith("file://")) {
            // Если префикс "file://" содержится в пути к файлу, удаляем его
            val imagePath = imageIdentifier.substring("file://".length)
            val imageFile = File(imagePath)

            if (imageFile.exists()) {
                // Если изображение найдено во внутреннем хранилище, загрузите его из этого места
                val drawable = Drawable.createFromPath(imageFile.absolutePath)
                holder.image.setImageDrawable(drawable)
                return
            }
        }

        // Если изображение не найдено во внутреннем хранилище, попробуйте загрузить его из ресурсов папки drawable
        val imageId =
            context.resources.getIdentifier(imageIdentifier, "drawable", context.packageName)
        if (imageId != 0) {
            // Если изображение найдено в папке drawable, загрузите его из ресурсов
            holder.image.setImageResource(imageId)
        } else {
            // Если изображение не найдено ни во внутреннем хранилище, ни в папке drawable,
            // то можно установить какое-то заглушечное изображение или оставить ImageView пустым
            // holder.image.setImageResource(R.drawable.placeholder)
            holder.image.setImageDrawable(null)
        }

    }
}