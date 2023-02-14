import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import NavBar from "../NavBar/NavBar";
import FileUpload from "../FileUpload/FileUpload";

function App() {
  return (
    <div>
      <NavBar />
      <div className='container'>
        <FileUpload />
      </div>
    </div>
  );
}

export default App;
