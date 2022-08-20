package com.khlafawi.todoroom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.khlafawi.todoroom.MainViewModel
import com.khlafawi.todoroom.R
import com.khlafawi.todoroom.databinding.FragmentNewTodoBinding
import com.khlafawi.todoroom.model.ToDoItem

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NewTodoFragment : Fragment() {

    private var _binding: FragmentNewTodoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel
    private var oldItem = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewTodoBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentItem = if (arguments == null) {
            ToDoItem()
        } else {
            if (requireArguments().get("selectedTodoItem") == null) {
                ToDoItem()
            } else {
                oldItem = true
                requireArguments().get("selectedTodoItem") as ToDoItem
            }
        }

        binding.todoItem = currentItem
        binding.oldItem = oldItem
        binding.buttonSave.setOnClickListener {
            val id = binding.todoItem?.id ?: 0
            val title = binding.todoItem?.title ?: ""
            val priority = binding.todoItem?.priority ?: -1

            if (title.isEmpty()) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.valid_title),
                    Toast.LENGTH_LONG
                )
                    .show()
                return@setOnClickListener
            }

            if (priority == -1) {
                Toast.makeText(
                    context,
                    resources.getString(R.string.valid_priority),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val message = if (oldItem) {
                viewModel.updateCurrentItem(id, title, priority)
                resources.getString(R.string.updated_successfully)
            } else {
                viewModel.addNewTodoItem(title, priority)
                resources.getString(R.string.added_successfully)
            }
            findNavController().navigate(NewTodoFragmentDirections.actionAddNewItemToListItems())
            Toast.makeText(
                context,
                message,
                Toast.LENGTH_LONG
            ).show()
        }

        binding.buttonDelete.setOnClickListener {
            viewModel.deleteCurrentItem(currentItem)
            findNavController().navigate(NewTodoFragmentDirections.actionAddNewItemToListItems())
            Toast.makeText(
                context,
                resources.getString(R.string.deleted_successfully),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}