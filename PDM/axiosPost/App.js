import React, { useEffect, useState } from "react";
import axios from "axios";
import {TextInput,View,StyleSheet,Button,FlatList,Text,StatusBar} from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";

export default function App() {
  const [users, setUsers] = useState([]);
  const [newName, setNewName] = useState("");
  const [newEmail, setNewEmail] = useState("");

  const fetchUser = async ()=>{
    try {
      const response = await axios.get(process.env.EXPO_PUBLIC_API_URL);

      if(response.status === 200){
        setUsers(response.data);
      }

    } catch (e) {
      console.error("Erro ao carregar usuarios", e.message);
    }
  }

  const addUser = async () => {
    if (!newName || !newEmail) { 
      alert("Por favor, preencha nome e email.");
      return;
    }
    try {
      const response = await axios.post(process.env.EXPO_PUBLIC_API_URL, {
        name: newName,
        email: newEmail,
      });

      if (response.status === 201 || response.status === 200) { 
        return response.data;
      }
    } catch (e) {
      console.error("Erro ao fazer requisição", e.message);
      alert("Não foi possível adicionar o usuário.");
    }
  };

  const handlerAddUser = async () => {
    const result = await addUser();
    if (result) { 
      setUsers([...users, result]);
      setNewName("");
      setNewEmail("");
    }
  };

  useEffect(()=>{
    fetchUser()
  },[])

  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <StatusBar barStyle="light-content" />
        <Text style={styles.title}>Cadastro de Usuários</Text>

        <View style={styles.formContainer}>
          <Text style={styles.label}>Nome:</Text>
          <TextInput
            style={styles.input}
            placeholder="Digite o nome do usuário"
            placeholderTextColor="#888" 
            value={newName}
            onChangeText={setNewName}
          />
          <Text style={styles.label}>Email:</Text>
          <TextInput
            style={styles.input}
            placeholder="Digite o e-mail do usuário"
            placeholderTextColor="#888"
            value={newEmail}
            onChangeText={setNewEmail}
            keyboardType="email-address" 
            autoCapitalize="none"
          />
          <Button
            title="Adicionar Usuário"
            onPress={handlerAddUser}
            color="#6200ee" 
          />
        </View>

        <FlatList
          data={users}
          style={styles.list}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <View style={styles.userItem}>
              <Text style={styles.userName}>{item.name}</Text>
              <Text style={styles.userEmail}>{item.email}</Text>
            </View>
          )}
        />
      </SafeAreaView>
    </SafeAreaProvider>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#121212', 
    padding: 20,
  },
  title: {
    fontSize: 26,
    fontWeight: 'bold',
    color: '#FFFFFF',
    alignSelf: 'center',
    marginBottom: 20,
  },
  formContainer: {
    width: '100%',
    marginBottom: 20,
  },
  label: {
    fontSize: 16,
    color: '#BBBBBB',
    marginBottom: 5,
  },
  input: {
    backgroundColor: '#1E1E1E',
    color: '#FFFFFF',
    borderWidth: 1,
    borderColor: '#333333',
    borderRadius: 8,
    paddingHorizontal: 15,
    paddingVertical: 12,
    fontSize: 16,
    marginBottom: 15,
  },
  list: {
    width: '100%',
  },
  userItem: {
    backgroundColor: '#1E1E1E',
    padding: 15,
    borderRadius: 8,
    marginBottom: 10,
    borderLeftWidth: 4,
    borderLeftColor: '#6200ee', 
  },
  userName: {
    fontSize: 18,
    fontWeight: 'bold',
    color: '#FFFFFF',
  },
  userEmail: {
    fontSize: 14,
    color: '#AAAAAA',
    marginTop: 4,
  },
});