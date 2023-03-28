import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kumar.R

class ExerciseAdapter(private val exercises: List<Exercise>) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val typeTextView: TextView = itemView.findViewById(R.id.type_text_view)
        val repetitionsTextView: TextView = itemView.findViewById(R.id.repetitions_text_view)
        val weightTextView: TextView = itemView.findViewById(R.id.weight_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]
        holder.typeTextView.text = exercise.exerciseType
        holder.repetitionsTextView.text = exercise.repetitions.toString()
        holder.weightTextView.text = exercise.weight.toString()
    }

    override fun getItemCount() = exercises.size
}

data class Exercise(val exerciseType: String, val repetitions: Int, val weight: Int)
