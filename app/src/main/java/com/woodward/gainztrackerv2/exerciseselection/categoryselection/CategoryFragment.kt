package com.woodward.gainztrackerv2.exerciseselection.categoryselection

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.woodward.gainztrackerv2.R
import com.woodward.gainztrackerv2.databinding.FragmentCategoryPageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {

    private val categoryFragViewModel: CategoryViewModel by viewModels()

    private lateinit var adapter: CategoryAdapter

    private var _binding: FragmentCategoryPageBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner
        setUpNavigation()
        setUpAdapter()
        setUpListObserver()
        setUpItemTouchHelper()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryPageBinding.inflate(inflater, container, false).apply {
            categoryViewModel = categoryFragViewModel
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setUpNavigation() {
        categoryFragViewModel.navigateToExerciseType.observe(viewLifecycleOwner, Observer { cat ->
            cat?.let {
                this.findNavController()
                    .navigate(CategoryFragmentDirections.actionCategoryPageToExerciseTypePage(cat))
                categoryFragViewModel.onCategoryToExerciseTypeNavigated()
            }
        })
    }

    private fun setUpAdapter() {
        adapter = CategoryAdapter(CategoryAdapterListener { catID ->
            categoryFragViewModel.onCategorySelected(catID)
        })
        binding.categoryRecyclerView.adapter = adapter
    }

    private fun setUpListObserver() {
        categoryFragViewModel.listOfCategories.observe(viewLifecycleOwner, Observer { categories ->
            categories?.let {
                adapter.submitList(categories)
            }
        })
    }

    private fun setUpItemTouchHelper() {
        val itemTouchHelperCallback =
            object :
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    categoryFragViewModel.onDelete(adapter.getCategoryPosition(viewHolder.adapterPosition))
                    Toast.makeText(
                        context,
                        getString(R.string.Deleted_Category),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.categoryRecyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.new_item_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_item -> {
                findNavController().navigate(CategoryFragmentDirections.actionCategoryPageToAddNewCategory())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
