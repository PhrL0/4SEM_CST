import React, { useEffect, useState } from "react";
import axios from "axios";
import {TextInput,View,StyleSheet,Button,FlatList,Text,StatusBar,TouchableOpacity,} from "react-native";
import { SafeAreaProvider, SafeAreaView } from "react-native-safe-area-context";
import { PencilIcon} from "lucide-react-native";

export default function App() {
  const [users, setUsers] = useState([]);
  const [newName, setNewName] = useState("");
  const [newEmail, setNewEmail] = useState("");
  const [editingUser, setEditingUser] = useState(null);

  const fetchUsers = async () => {
    try {
      const response = await axios.get(process.env.EXPO_PUBLIC_API_URL);
      if (response.status === 200) {
        setUsers(response.data);
      }
    } catch (e) {
      console.error("Erro ao carregar usuários:", e.message);
    }
  };

  const updateUser = async (id) => {
    try {
      const response = await axios.put(`${process.env.EXPO_PUBLIC_API_URL}/${id}`, {
        name: newName,
        email: newEmail,
      });
      return response.data;
    } catch (e) {
      console.error("Erro ao atualizar:", e.message);
    }
  };

  const handleEdit = (user) => {
    setEditingUser(user);
    setNewName(user.name);
    setNewEmail(user.email);
  };
  
  const handleCancelEdit = () => {
    setEditingUser(null);
    setNewName("");
    setNewEmail("");
  };

  const handleUpdateSubmit = async () => {
    if (!editingUser) return; 

    const updatedUser = await updateUser(editingUser.id);
    if (updatedUser) {
      setUsers(
        users.map((u) => (u.id === editingUser.id ? updatedUser : u))
      );
    }
    handleCancelEdit();
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    <SafeAreaProvider>
      <SafeAreaView style={styles.container}>
        <StatusBar barStyle="light-content" />
        <Text style={styles.title}>PUT - Atualizar Usuários</Text>

        {editingUser && (
          <View style={styles.formContainer}>
            <Text style={styles.editingTitle}>Editando: {editingUser.name}</Text>
            <TextInput style={styles.input} placeholder="Nome" value={newName} onChangeText={setNewName} placeholderTextColor="#888" />
            <TextInput style={styles.input} placeholder="Email" value={newEmail} onChangeText={setNewEmail} keyboardType="email-address" autoCapitalize="none" placeholderTextColor="#888" />
            <View style={styles.buttonRow}>
              <Button
                title="Atualizar Usuário"
                onPress={handleUpdateSubmit}
                color="#6200ee"
              />
              <Button title="Cancelar" onPress={handleCancelEdit} color="#BBBBBB" />
            </View>
          </View>
        )}

        <FlatList
          data={users}
          style={styles.list}
          keyExtractor={(item) => item.id.toString()}
          renderItem={({ item }) => (
            <View style={styles.userItem}>
              <View style={styles.userInfo}>
                <Text style={styles.userName}>{item.name}</Text>
                <Text style={styles.userEmail}>{item.email}</Text>
              </View>
              <View style={styles.actionButtons}>
                <TouchableOpacity onPress={() => handleEdit(item)}>
                  <Text style={styles.buttonText}><PencilIcon fill="white"></PencilIcon></Text>
                </TouchableOpacity>
              </View>
            </View>
          )}
        />
      </SafeAreaView>
    </SafeAreaProvider>
  );
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: "#121212", padding: 20 },
  title: { fontSize: 26, fontWeight: "bold", color: "#FFFFFF", alignSelf: "center", marginBottom: 20 },
  formContainer: { width: "100%", marginBottom: 20, backgroundColor: '#2a2a2a', padding: 15, borderRadius: 8 },
  editingTitle: { fontSize: 18, fontWeight: 'bold', color: '#FFFFFF', alignSelf: 'center', marginBottom: 15 },
  input: { backgroundColor: "#1E1E1E", color: "#FFFFFF", borderWidth: 1, borderColor: "#333333", borderRadius: 8, paddingHorizontal: 15, paddingVertical: 12, fontSize: 16, marginBottom: 15 },
  buttonRow: { flexDirection: 'row', justifyContent: 'space-around' },
  list: { width: "100%" },
  userItem: {
    backgroundColor: "#1E1E1E",
    padding: 15,
    borderRadius: 8,
    marginBottom: 10,
    flexDirection: "row",
    justifyContent: "space-between",
    alignItems: "center",
  },
  userInfo: {
    flex: 1,
  },
  userName: { fontSize: 18, fontWeight: "bold", color: "#FFFFFF" },
  userEmail: { fontSize: 14, color: "#AAAAAA", marginTop: 4 },
  actionButtons: {
    flexDirection: "row",
    marginLeft: 15,
  },
  buttonText: {
    fontSize: 20,
    paddingHorizontal: 10,
  },
});