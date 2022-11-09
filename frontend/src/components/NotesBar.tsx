import {useMutation, useQuery} from "@apollo/client";
import {ADD_NOTE, GET_NOTES} from "../GraphQLQueries";
import NoteDto from "../dtos/NoteDto";
import Note from "./Note";
import {Button, Card, FloatingLabel, Form, InputGroup} from "react-bootstrap";
import {useEffect, useState} from "react";
import {client} from "../index";

import "./NotesBar.css";

export default function NotesBar(props: any) {
    const [noteName, setNoteName] = useState("");
    const [noteContent, setNoteContent] = useState("");

    const addNoteHandler = async () => {
        let errorOccurred = false;

        await addNote({
            variables: {
                name: noteName,
                content: noteContent,
                categoryId: props.categoryId
            },
            onError: (error) => {
                errorOccurred = true;
                alert(error.message);
            }
        });

        if (errorOccurred) {
            return;
        }

        await setNoteName("");

        await setNoteContent("");

        await client.refetchQueries({include: [GET_NOTES]});
    }

    const [addNote, addNoteResult] = useMutation(ADD_NOTE);

    useEffect(() => {
        if (addNoteResult.data) {
            setNoteName("");
            setNoteContent("");
        }
    }, [addNoteResult.data]);

    const {loading, error, data} = useQuery(GET_NOTES, {
        variables: {
            categoryId: props.categoryId
        }
    });

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div className="notesContainer">
            {!error && data.notes.map((note: NoteDto) => (
                <Note note={note} className="note"/>
            ))}
            <Card style={{width: '18rem'}}>
                <Card.Body>
                    <Card.Title>
                        <InputGroup className="mb-3">
                            <FloatingLabel controlId="floatingInput" label="Note name" children={
                                <Form.Control
                                    placeholder="Note name"
                                    aria-label="Note name"
                                    aria-describedby="note-name"
                                    value={noteName}
                                    onChange={(event: any) => setNoteName(event.target.value)}
                                />
                            }/>
                        </InputGroup>
                    </Card.Title>
                    <Card.Text>
                        <InputGroup className="mb-3">
                            <FloatingLabel controlId="floatingInput" label="Note content" children={
                                <Form.Control
                                    as="textarea"
                                    placeholder="Note content"
                                    aria-label="Note content"
                                    aria-describedby="note-content"
                                    value={noteContent}
                                    onChange={(event: any) => setNoteContent(event.target.value)}
                                />
                            }/>
                        </InputGroup>
                    </Card.Text>
                    <Button variant="success" onClick={addNoteHandler}>Add</Button>
                </Card.Body>
            </Card>
        </div>
    );
}