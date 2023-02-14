import http from '../http-common'

class FileUploadService {
    
    // upload file
    upload(file, onUploadProgress) {
      let formData = new FormData();
  
      formData.append("file", file);
  
      return http.post("/upload", formData, {
        headers: {
          "Content-Type": "multipart/form-data",
        },
        onUploadProgress,
      });
    }
  
    // get files list
    getFiles() {
      return http.get("/files");
    }
  }
  
  export default new FileUploadService();