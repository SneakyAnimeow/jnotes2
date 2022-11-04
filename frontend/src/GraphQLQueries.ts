import {gql} from "@apollo/client";

export const GET_CATEGORIES = gql`
    query{
        categories{
            id,
            name
        }
    }
`;

export const GET_NOTES = gql`
    query notes($categoryId: Int){
        notes(categoryId: $categoryId){
            id,
            name,
            content,
        }
    }
`;

export const ADD_CATEGORY = gql`
    mutation addCategory($name: String!){
        addCategory(name: $name){
            id,
            name
        }
    }
`;

export const ADD_NOTE = gql`
    mutation addNote($name: String!, $content: String!, $categoryId: Int!){
        addNote(name: $name, content: $content, categoryId: $categoryId){
            id,
            name,
            content
        }
    }
`;

export const DELETE_CATEGORY = gql`
    mutation deleteCategory($id: Int!){
        deleteCategory(id: $id){
            id,
            name
        }
    }
`;

export const DELETE_NOTE = gql`
    mutation deleteNote($id: Int!){
        deleteNote(id: $id){
            id,
            name,
            content
        }
    }
`;