import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.booktarang.R
import com.example.booktarang.model.Field

class FieldAdapter(private val fields: List<Field>) : RecyclerView.Adapter<FieldAdapter.FieldViewHolder>() {

    class FieldViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFieldName: TextView = itemView.findViewById(R.id.tvFieldName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FieldViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_field, parent, false)
        return FieldViewHolder(view)
    }

    override fun onBindViewHolder(holder: FieldViewHolder, position: Int) {
        holder.tvFieldName.text = fields[position].name
    }

    override fun getItemCount(): Int = fields.size
}