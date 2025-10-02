import { CameraView, useCameraPermissions } from "expo-camera";
import { useEffect, useRef, useState } from "react";
import { Button, Image, StyleSheet, Text, TouchableOpacity, View } from "react-native";

export default function CameraComponent(){

  const [facing, setFacing] = useState('back');
  const [permission, requestPermission] = useCameraPermissions();
  const [capturedphoto, setCapturedPhoto] = useState(null);
  const cameraRef = useRef(null);

  useEffect(() => {
    requestPermission();
  }, []) 

  if(!permission){
    return <View/>
  }

  if (!permission.granted){
    return(
      <View style={styles.container}>
        <Text style={{textAlign:'center'}}>preciso de sua permissão para acesso à câmera</Text>
        <Button onPress={requestPermission} title="conceder permissão" />
      </View>
    );
  }

  function toggleCameraFacing(){
    setFacing(current => (current === 'back' ? 'front': 'back'));
  }

  async function takePicture() {
    if (cameraRef.current){
      const photo = await cameraRef.current.takePicture(); 
      setCapturedPhoto(photo);
      console.log(photo.uri);
    }
  }

  if(capturedphoto){
    return(
      <View style={styles.previewContainer}>
        <View style={styles.buttonContainer}>
          <Button 
            title="tirar outra foto" 
            onPress={() => setCapturedPhoto(null)}
          />
        </View> 
        <Image 
          source={{uri: capturedphoto.uri}}
          style={styles.preview}
        />
      </View> 
    );
  }

  return (
    <View style={styles.cameraContainer}>
      <CameraView style={styles.camera} facing={facing} ref={cameraRef}>
        <View style={styles.controlsContainer}>
            <TouchableOpacity style={styles.flipButton} onPress={toggleCameraFacing}>
                <Text style={styles.text}>virar camera</Text>
            </TouchableOpacity>
            <TouchableOpacity style={styles.captureButton} onPress={takePicture}>
                <Text style={styles.text}>tirar foto</Text>
            </TouchableOpacity>
        </View>
      </CameraView>
    </View>
  );
}


const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        padding: 20,
    },
    cameraContainer: {
        flex: 1,
    },
    camera: {
        flex: 1,
    },
    controlsContainer: {
        flex: 1,
        flexDirection: 'row',
        backgroundColor: 'transparent',
        margin: 20,
        justifyContent: 'space-between',
        alignItems: 'flex-end',
    },
    text: {
        fontSize: 24,
        fontWeight: 'bold',
        color: 'white',
    },
    flipButton: {
        backgroundColor: '#000000AA',
        padding: 10,
        borderRadius: 5,
    },
    captureButton: {
        backgroundColor: 'red',
        padding: 10,
        borderRadius: 50,
        width: 60,
        height: 60,
        justifyContent: 'center',
        alignItems: 'center',
    },
    previewContainer: {
        flex: 1,
        justifyContent: 'flex-start',
    },
    buttonContainer: {
        padding: 10,
        backgroundColor: '#fff',
    },
    preview: {
        flex: 1,
        resizeMode: 'contain',
    },
});