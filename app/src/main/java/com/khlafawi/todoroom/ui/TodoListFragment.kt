package com.khlafawi.todoroom.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.khlafawi.todoroom.MainViewModel
import com.khlafawi.todoroom.R
import com.khlafawi.todoroom.databinding.FragmentTodoListBinding
import androidx.lifecycle.ViewModelProvider
import com.khlafawi.todoroom.databinding.ItemTodoBinding
import com.khlafawi.todoroom.helpers.OnToDoItemClickedListener


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class TodoListFragment : Fragment() {

    private var _binding: FragmentTodoListBinding? = null
    //private val noteDatabase by lazy { ToDoDatabase.getInstance(requireContext()).todoDataBaseDao }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        setupItemsView(inflater, container)
        setupItemNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNewItem.setOnClickListener {
            findNavController().navigate(TodoListFragmentDirections.actionListItemsToAddNewItem(null))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupItemsView(inflater: LayoutInflater, container: ViewGroup?) {
        viewModel.allItemsSorted.observe(viewLifecycleOwner) { itemsList ->
            binding.todoList.removeAllViews()
            for (todoItem in itemsList) {
                val itemsBinding: ItemTodoBinding =
                    DataBindingUtil.inflate(inflater, R.layout.item_todo, container, false)
                itemsBinding.todoItem = todoItem
                itemsBinding.onClickListener = OnToDoItemClickedListener {
                    viewModel.onToDoItemClicked(it)
                }
                binding.todoList.addView(itemsBinding.root)
            }
        }
    }

    private fun setupItemNavigation() {
        viewModel.navigateToTodoItemDetails.observe(viewLifecycleOwner) { todoItem ->
            todoItem?.let {
                this.findNavController()
                    .navigate(TodoListFragmentDirections.actionListItemsToAddNewItem(todoItem))
                viewModel.onToDoDetailsDetailsNavigated()
            }
        }
    }
}