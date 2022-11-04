import NoteDto from "../dtos/NoteDto";
import {Button, Card} from "react-bootstrap";
import {useMutation} from "@apollo/client";
import {DELETE_NOTE, GET_NOTES} from "../GraphQLQueries";
import {client} from "../index";

export default function Note(props: any){
    const note: NoteDto = props.note;

    const handleDelete = async () => {
        let errorOccurred = false;

        await deleteNote({
            variables: {
                id: note.id
            },
            onError: (error) => {
                errorOccurred = true;
                alert(error.message);
            }
        });

        if (errorOccurred) {
            return;
        }

        await client.refetchQueries({include: [GET_NOTES]});
    }

    const [deleteNote] = useMutation(DELETE_NOTE);

    return (
        <Card style={{ width: '18rem' }}>
            <Card.Body>
                <Card.Title>{note.name}</Card.Title>
                <Card.Text>{note.content}</Card.Text>
                <Button variant="danger" onClick={handleDelete}>🗑️</Button>
            </Card.Body>
        </Card>
    );
}