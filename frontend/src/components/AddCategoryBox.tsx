import {Button, Card, FloatingLabel, Form, InputGroup} from "react-bootstrap";
import {useState} from "react";
import {ADD_CATEGORY, GET_CATEGORIES} from "../GraphQLQueries";
import {useMutation} from "@apollo/client";
import {client} from "../index";

export default function AddCategoryBox() {
    const [categoryName, setCategoryName] = useState("");

    const [addCategory] = useMutation(ADD_CATEGORY);

    const handleCategoryNameChange = (event: any) => {
        setCategoryName(event.target.value);
    }

    const handleAddCategory = async () => {
        let errorOccurred = false;

        await addCategory({
            variables: {
                name: categoryName
            },
            onError: (error) => {
                errorOccurred = true;
                alert(error.message);
            }
        });

        if (errorOccurred) {
            return;
        }

        await setCategoryName("");

        await client.refetchQueries({include: [GET_CATEGORIES]});
    }

    return (
        <div>
            <Card style={{width: '18rem'}}>
                <Card.Body>
                    <InputGroup className="mb-3">
                        <FloatingLabel controlId="floatingInput" label="Category name" children={
                            <Form.Control
                                placeholder="Category name"
                                aria-label="Category name"
                                aria-describedby="category-name"
                                value={categoryName}
                                onChange={handleCategoryNameChange}
                            />
                        }/>
                    </InputGroup>
                    <Button variant="success" onClick={handleAddCategory}>Add</Button>
                </Card.Body>
            </Card>
        </div>
    );
}