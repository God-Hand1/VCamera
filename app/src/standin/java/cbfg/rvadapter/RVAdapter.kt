package cbfg.rvadapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Local stand-in for the JCenter/Gitee-only `com.gitee.cbfg5210:RVAdapter:0.3.7`
 * artifact, which can no longer be resolved from any live Maven repository.
 *
 * It reproduces the exact API surface used by VCamera's adapters and screens:
 * [RVAdapter] constructor, [RVAdapter.bind], [RVAdapter.setItemClickListener],
 * [RVAdapter.setItemLongClickListener], [RVAdapter.setItems],
 * [RVAdapter.getItems], [RVAdapter.replaceAt], plus inherited
 * [RecyclerView.Adapter.notifyItemMoved]; and the [RVHolder]/[RVHolderFactory]
 * abstract contracts implemented by AppsAdapter, ListAdapter and GmsAdapter.
 */
abstract class RVHolderFactory {
    abstract fun createViewHolder(parent: ViewGroup?, viewType: Int, item: Any): RVHolder<out Any>
}

abstract class RVHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun setContent(item: T, isSelected: Boolean, payload: Any?)
}

class RVAdapter<T>(
    @Suppress("UNUSED_PARAMETER") context: Context,
    private val factory: RVHolderFactory
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<T>()
    private var clickListener: ((view: View, item: T, position: Int) -> Unit)? = null
    private var longClickListener: ((view: View, item: T, position: Int) -> Unit)? = null

    fun bind(recyclerView: RecyclerView): RVAdapter<T> {
        recyclerView.adapter = this
        return this
    }

    fun setItemClickListener(listener: (view: View, item: T, position: Int) -> Unit): RVAdapter<T> {
        clickListener = listener
        return this
    }

    fun setItemLongClickListener(listener: (view: View, item: T, position: Int) -> Unit): RVAdapter<T> {
        longClickListener = listener
        return this
    }

    fun setItems(newItems: List<T>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItems(): MutableList<T> = items

    fun replaceAt(index: Int, item: T) {
        items[index] = item
        notifyItemChanged(index)
    }

    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val probe: Any = items.firstOrNull() ?: Any()
        return factory.createViewHolder(parent, viewType, probe)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        (holder as RVHolder<T>).setContent(item, holder.itemView.isSelected, null)

        holder.itemView.setOnClickListener { view ->
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                clickListener?.invoke(view, items[pos], pos)
            }
        }
        holder.itemView.setOnLongClickListener { view ->
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION && longClickListener != null) {
                longClickListener!!.invoke(view, items[pos], pos)
                true
            } else {
                false
            }
        }
    }
}
