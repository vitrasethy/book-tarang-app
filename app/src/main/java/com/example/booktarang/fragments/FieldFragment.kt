import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.booktarang.R
import com.example.booktarang.api.ApiManager
import com.example.booktarang.model.Field
import kotlinx.coroutines.launch

class FieldFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var fieldAdapter: FieldAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_field, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        fetchFields()
        return view
    }

    private fun fetchFields() {
        val apiService = ApiManager.getApiService()

        lifecycleScope.launch {
            try {
                val response = apiService.getFields()
                val fields = response.data ?: emptyList()
                fieldAdapter = FieldAdapter(fields)
                recyclerView.adapter = fieldAdapter
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}