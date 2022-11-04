export default class NoteDto{
    public id: number;
    public name: string;
    public content: string;

    constructor(id: number, name: string, content: string){
        this.id = id;
        this.name = name;
        this.content = content;
    }
}