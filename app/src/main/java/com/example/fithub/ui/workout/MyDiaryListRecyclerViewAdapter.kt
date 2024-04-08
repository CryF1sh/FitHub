import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.models.DiaryEntry

class MyDiaryListRecyclerViewAdapter(
    private val values: MutableList<DiaryEntry>
) : RecyclerView.Adapter<MyDiaryListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_diary, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateView: TextView = itemView.findViewById(R.id.textViewDate)
        private val titleView: TextView = itemView.findViewById(R.id.textViewTitle)

        fun bind(item: DiaryEntry) {
            dateView.text = item.date
            titleView.text = item.title
        }
    }
}
