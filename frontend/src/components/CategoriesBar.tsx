import {useState} from "react";
import {useMutation, useQuery} from "@apollo/client";
import {DELETE_CATEGORY, GET_CATEGORIES} from "../GraphQLQueries";
import CategoryDto from "../dtos/CategoryDto";
import NotesBar from "./NotesBar";
import {Button, Navbar} from "react-bootstrap";
import AddCategoryBox from "./AddCategoryBox";
import Cookies from "js-cookie";
import {client} from "../index";

import "./CategoriesBar.css";

export default function CategoriesBar() {
    const [selectedCategoryId, setSelectedCategoryId] = useState(-1);

    const [addCategoryBoxVisible, setAddCategoryBoxVisible] = useState(false);

    const {loading, error, data} = useQuery(GET_CATEGORIES);

    const [deleteCategory] = useMutation(DELETE_CATEGORY);

    setTimeout(() => {
        if (error) {
            Cookies.remove("token");
            window.location.reload();
        }
    }, 2500);

    const handleAddCategory = () => {
        setAddCategoryBoxVisible(!addCategoryBoxVisible);
    };

    if (loading) {
        return <p>Loading...</p>;
    }

    return (
        <div>
            {error && <p>Session expired, redirecting...</p>}

            <Navbar bg="primary" variant="dark">
                <div className="text-nowrap overflow-auto">
                    {!error &&
                        <Button variant={addCategoryBoxVisible ? "danger" : "success"}
                                onClick={handleAddCategory}>{addCategoryBoxVisible ? "-" : "+"}</Button>
                    }
                    {!error && data.categories.map((category: CategoryDto) => (
                        <Button key={category.id} variant={addCategoryBoxVisible ? "warning" : "primary"}
                                onClick={async () => {
                                    if (!addCategoryBoxVisible) {
                                        await setSelectedCategoryId(category.id);
                                        return;
                                    }

                                    let errorOccurred = false;

                                    await deleteCategory({
                                        variables: {
                                            id: category.id
                                        },
                                        onError: (error) => {
                                            errorOccurred = true;
                                            alert(error.message);
                                        }
                                    });

                                    if (errorOccurred) {
                                        return;
                                    }

                                    await client.refetchQueries({include: [GET_CATEGORIES]});
                                }}>{category.name}</Button>
                    ))}
                </div>
            </Navbar>

            {!error && addCategoryBoxVisible && <AddCategoryBox/>}

            {selectedCategoryId !== -1 &&
                <NotesBar categoryId={selectedCategoryId}/>
            }
        </div>
    );
}