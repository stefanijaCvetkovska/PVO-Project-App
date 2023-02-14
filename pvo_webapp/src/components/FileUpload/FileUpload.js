import React, { Component } from "react";
import Card from 'react-bootstrap/Card';
import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import ProgressBar from 'react-bootstrap/ProgressBar';
import Badge from 'react-bootstrap/Badge';
import CloudDownloadIcon from '@mui/icons-material/CloudDownload';

import FileUploadService from "../../service/FileUploadService";
import { ListGroup } from "react-bootstrap";


export default class FileUpload extends Component {
    constructor(props) {
        super(props);
        this.selectFile = this.selectFile.bind(this);
        this.upload = this.upload.bind(this);

        this.state = {
            selectedFiles: undefined,
            currentFile: undefined,
            progress: 0,
            message: "",
            fileInfos: [],
        };
    }

    componentDidMount() {
        FileUploadService.getFiles().then((response) => {
            this.setState({
                fileInfos: response.data,
            });
        });
    }


    selectFile(event) {
        this.setState({
            selectedFiles: event.target.files,
        });
    }

    upload() {
        let currentFile = this.state.selectedFiles[0];

        this.setState({
            progress: 0,
            currentFile: currentFile,
        });

        FileUploadService.upload(currentFile, (event) => {
            this.setState({
                progress: Math.round((100 * event.loaded) / event.total)
            });
        })
            .then((response) => {
                this.setState({
                    message: response.data.message,
                });
                return FileUploadService.getFiles();
            })
            .then((files) => {
                this.setState({
                    fileInfos: files.data,
                });
            })
            .catch(() => {
                this.setState({
                    progress: 0,
                    message: "Could not upload the file!",
                    currentFile: undefined,
                });
            });

        this.setState({
            selectedFiles: undefined,
        });
    }

    render() {
        const {
            selectedFiles,
            currentFile,
            progress,
            message,
            fileInfos,
            isError
        } = this.state;

        return (
            <div className="my-5">
                    <Card className="mb-2 bg-light">
                        <Card.Header className="h4">Upload file</Card.Header>
                        <Card.Body>
                            <Card.Text>
                                <Form.Group className="mb-3">
                                    <Form.Label htmlFor="btn-upload">Function: Sort file by date ascending</Form.Label>
                                    <Form.Control type="file"
                                        id="btn-upload"
                                        name="btn-upload"
                                        onChange={this.selectFile} />
                                </Form.Group>



                                <div className="d-grid gap-2">
                                    <Button className="btn-dark"
                                        variant="contained"
                                        component="span"
                                        disabled={!selectedFiles}
                                        onClick={this.upload}>
                                        Upload
                                    </Button>
                                </div>

                                <div className="my-2">
                                    <Badge bg="dark"
                                        fontSize={"large"}
                                        className={`upload-message ${isError ? "error" : ""}`}>
                                        {message}
                                    </Badge>
                                </div>


                            </Card.Text>
                        </Card.Body>
                        <Card.Footer>
                            {currentFile && (
                                <ProgressBar now={progress}
                                    label={`${progress}%`}
                                    variant="success" />
                            )}

                        </Card.Footer>
                    </Card>



                    <Card className=" my-5">
                        <Card.Header className="h4">Download uploaded and sorted files</Card.Header>
                        <Card.Body>
                            <ListGroup variant="flush">
                                {fileInfos &&
                                    fileInfos.map((file, index) => (
                                        <ListGroup.Item className="list-group-item" key={index}>
                                            <CloudDownloadIcon fontSize="large"
                                                className="mx-4">

                                            </CloudDownloadIcon>
                                            <a href={file.url}
                                                className="text-decoration-none">
                                                {file.name}</a>
                                        </ListGroup.Item>
                                    ))}
                            </ListGroup>
                        </Card.Body>
                    </Card>
            </div >
        );
    }
}