import androidx.room.Dao
import com.example.gymapp.data.WorkoutNotes
import com.example.gymapp.data.WorkoutNotesDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

@Dao
class FakeWorkoutNotesDao : WorkoutNotesDao {
    private val notesList = mutableListOf<WorkoutNotes>()
    private val notesFlow = MutableStateFlow<List<WorkoutNotes>>(emptyList())

    override suspend fun insertNotes(notes: WorkoutNotes) {
        // Replace existing notes for this date
        notesList.removeAll { it.date == notes.date }
        notesList.add(notes)
        notesFlow.emit(notesList.toList())
    }

    suspend fun updateNotes(notes: WorkoutNotes) {
        val index = notesList.indexOfFirst { it.date == notes.date }
        if (index != -1) {
            notesList[index] = notes
            notesFlow.emit(notesList.toList())
        }
    }

    suspend fun deleteNotes(notes: WorkoutNotes) {
        notesList.removeAll { it.date == notes.date }
        notesFlow.emit(notesList.toList())
    }

    override suspend fun deleteNotesForDate(date: LocalDate) {
        notesList.removeAll { it.date == date }
        notesFlow.emit(notesList.toList())
    }

    override fun getNotesForDate(date: LocalDate): WorkoutNotes? {
        return notesList.firstOrNull { it.date == date }
    }

    fun getAllNotes(): Flow<List<WorkoutNotes>> {
        return notesFlow
    }

    // Helper function for tests
    fun clear() {
        notesList.clear()
        notesFlow.value = emptyList()
    }
}