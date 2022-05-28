package eu.hanna.memomvvm.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import eu.hanna.memomvvm.MainActivity
import eu.hanna.memomvvm.R
import eu.hanna.memomvvm.databinding.FragmentUpdateNoteBinding
import eu.hanna.memomvvm.helper.toast
import eu.hanna.memomvvm.model.Note
import eu.hanna.memomvvm.viewmodel.NoteViewModel

class UpdateNoteFragment : Fragment() {

    private var _binding: FragmentUpdateNoteBinding?= null
    private val binding get() = _binding!!

    // Create to update the items at UpdateNoteFragment
    private val args: UpdateNoteFragmentArgs by navArgs()
    private lateinit var currentNote: Note
    private lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateNoteBinding.inflate(inflater,container,false)
        return binding.root
    }

    // Create to update the items at UpdateNoteFragment
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // The items in cards can be seen in the updatefragment
        noteViewModel = (activity as MainActivity).noteViewModel
        currentNote = args.note!!
        binding.etNoteBodyUpdate.setText(currentNote.noteBody)
        binding.etNoteTitleUpdate.setText(currentNote.noteTitle)

        binding.fabDone.setOnClickListener {
            val title = binding.etNoteTitleUpdate.text.toString().trim()
            val body = binding.etNoteBodyUpdate.text.toString().trim()

            if (title.isNotEmpty()) {
                val note = Note(currentNote.id,title,body)
                noteViewModel.updateNote(note)
                activity?.toast("Note is updated!")

                view.findNavController().navigate(R.id.action_updateNoteFragment_to_homeFragment)
            } else {
                activity?.toast("Enter a note title")
            }
        }
    }

    private fun deleteNote() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Are you sure you want to delete permanently")
            setPositiveButton("DELETE") { _,_ ->
                noteViewModel.deleteNote(currentNote)
                view?.findNavController()?.navigate(
                    R.id.action_updateNoteFragment_to_homeFragment
                )
            }
            setNegativeButton("CANCEL",null)
        }.create().show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_delete -> {
                deleteNote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_note,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}