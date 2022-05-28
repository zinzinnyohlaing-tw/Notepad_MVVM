package eu.hanna.memomvvm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import eu.hanna.memomvvm.databinding.ActivityMainBinding
import eu.hanna.memomvvm.db.NoteDatabase
import eu.hanna.memomvvm.repository.NoteRepository
import eu.hanna.memomvvm.viewmodel.NoteViewModel
import eu.hanna.memomvvm.viewmodel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Create instance of NoteViewModel
    lateinit var noteViewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setUpViewModel()

    }

    private fun setUpViewModel(){

        val noteRepository = NoteRepository(NoteDatabase(this))

        val viewModelProviderFactory = NoteViewModelProviderFactory(application,noteRepository)

        noteViewModel = ViewModelProvider(this,viewModelProviderFactory).get(NoteViewModel::class.java)

    }
}