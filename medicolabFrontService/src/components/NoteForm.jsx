import React, { useState } from 'react'
import { toastError, toastSuccess } from '../api/ToastService';
import { saveNote } from '../api/NoteService.js';

const NoteForm = ({patId, patientName, onNoteAdded}) => {
    const [noteText, setNoteText] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();
        if(!noteText.trim()) return;

        const newNote = {
            patId: parseInt(patId),
            patient: patientName,
            note: noteText,
        };

        try {
            const savedNote = await saveNote(newNote);
            toastSuccess('Note ajoutée');
            setNoteText('');
            if (onNoteAdded) onNoteAdded(savedNote.data);
        } catch (error) {
            console.error(error);
            toastError("Erreur lors de l'ajout de la note");
        }
    }

  return (
    <div className = "add-note-form" style={{marginTop: '2rem'}}>
        <h3>Ajouter</h3>
        <form onSubmit={handleSubmit}>
            <div className='intput-box'>
                <textarea
                    value={noteText}
                    onChange={(e) => setNoteText(e.target.value)}
                    placeholder='Ecrire note ici...'
                    required
                    style={{ width: '100%', height: '80px'}}
                />
            </div>
            <button type='submit' className='btn'>Ajouter une note</button>
        </form>
    </div>
  )
}

export default NoteForm