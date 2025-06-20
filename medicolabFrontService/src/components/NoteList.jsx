import React from 'react'


const NoteList = ({notes, onDelete}) => {
  return (
    <main>
      <h2>Notes du patient ({notes.length})</h2>
      <table border="1" cellPadding="8" style={{ borderCollapse: "collapse", width: "100%" }}>
        <thead>
          <tr>
            <th>Patient</th>
            <th>Note</th>
          </tr>
        </thead>
        <tbody>
          {notes.map((note) => (
            <tr key={note.noteId}>
              <td>{note.patient}</td>
              <td>{note.note}</td>
              <td style={{ textAlign: 'center' }}>
                <button
                  onClick={() => onDelete(note.noteId)}
                  style={{
                    color: 'red',
                    fontWeight: 'bold',
                    border: 'none',
                    background: 'none',
                    cursor: 'pointer',
                  }}
                  title="Supprimer"
                >
                  ✖
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </main>
  )
}

export default NoteList