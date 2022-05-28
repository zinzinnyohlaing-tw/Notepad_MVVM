package eu.hanna.memomvvm.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import eu.hanna.memomvvm.MainActivity
import eu.hanna.memomvvm.R
import eu.hanna.memomvvm.databinding.FragmentNewNoteBinding
import eu.hanna.memomvvm.helper.toast
import eu.hanna.memomvvm.model.Note
import eu.hanna.memomvvm.viewmodel.NoteViewModel

class NewNoteFragment : Fragment() {

    private var _binding: FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    // Create instance of NoteViewModel
    private lateinit var noteViewModel: NoteViewModel
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        noteViewModel = (activity as MainActivity).noteViewModel
        mView = view
    }

    // Create the saveNote function
    private fun saveNote(view: View) {
        val noteTitle = binding.etNoteTitle.text.toString().trim()
        val noteBody = binding.etNoteBody.text.toString().trim()

        if (noteTitle.isNotEmpty()) {
            val note = Note(0,noteTitle,noteBody)

            noteViewModel.addNote(note)
            Snackbar.make(view,"Note Save Successfully",Snackbar.LENGTH_SHORT).show()

            view.findNavController().navigate(R.id.action_newNoteFragment_to_homeFragment)

        } else {
            activity?.toast("Please Enter Note title")
        }


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> {
                saveNote(mView)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_new_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}