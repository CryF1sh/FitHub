import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.models.WorkoutPlanListItem

class MyWorkoutPlanRecyclerViewAdapter(
    private val values: MutableList<WorkoutPlanListItem>
) : RecyclerView.Adapter<MyWorkoutPlanRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_workoutplan_itemlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameView: TextView = itemView.findViewById(R.id.textViewName)
        private val creatorView: TextView = itemView.findViewById(R.id.textViewCreator)

        fun bind(item: WorkoutPlanListItem) {
            nameView.text = item.name
            creatorView.text = item.creator
        }
    }
}
