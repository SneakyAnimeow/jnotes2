import "./Home.css"

export default function Home(props: any) {
    let token: String = props.token;

    return (
        <>
            <div className="generalTextContainer">
                <h1>
                    Welcome to JNotes!{" "}
                </h1>
                <p>
                    This is a simple note-taking app.{" "}
                </p>
                {!token && <p>
                    To get started, please <a href="/#/login">login</a>.{" "}
                </p>}
            </div>

            <footer>
                <p>
                    Source code available on <a href="https://github.com/SneakyAnimeow/jnotes2">GitHub</a>.{" "}
                </p>
            </footer>
        </>
    );
}