import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fithub.R
import com.example.fithub.ui.workout.WorkoutPlanFragmentDirections

class MyWorkoutPlanRecyclerViewAdapter(
    private val workoutPlanIDs: Map<Int, String>
) : RecyclerView.Adapter<MyWorkoutPlanRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_workoutplan_itemlist, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = workoutPlanIDs.entries.elementAt(position)
        holder.bind(item)
    }

    override fun getItemCount(): Int = workoutPlanIDs.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val nameView: TextView = itemView.findViewById(R.id.textViewName)
        private var planId: Int = -1

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(item: Map.Entry<Int, String>) {
            nameView.text = item.value
            planId = item.key
        }

        override fun onClick(v: View?) {
            val action = WorkoutPlanFragmentDirections.actionWorkoutPlansFragmentToWorkoutPlanEditFragment(planId)
            itemView.findNavController().navigate(action)
        }
    }
}
