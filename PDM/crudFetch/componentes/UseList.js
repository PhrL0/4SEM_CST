import { View, Text, Button, StyleSheet, FlatList, TouchableOpacity, Alert } from "react-native";

export default function UseList({ users, onUserChanged }) {

  const updateUser = async (id) => {
    try {
      const response = await fetch(`${process.env.EXPO_PUBLIC_API_URL}/${id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          name: "Nome Atualizado",
          email: "email.atualizado@example.com",
        }),
      });
      await response.json();
      onUserChanged(); 
    } catch (e) {
      console.log("Error updating user: ", e.message);
    }
  };

  const deleteUser = async (id) => {
    try {
      await fetch(`${process.env.EXPO_PUBLIC_API_URL}/${id}`, {
        method: "DELETE",
      });
      onUserChanged(); 
    } catch (e) {
      console.error("Error deleting user:", e.message);
    }
  };

  const confirmDelete = (id, name) => {
    Alert.alert(
      "Confirmar Exclusão",
      `Você tem certeza que deseja deletar ${name}?`,
      [
        { text: "Cancelar", style: "cancel" },
        { text: "Deletar", onPress: () => deleteUser(id), style: "destructive" },
      ],
      { cancelable: true }
    );
  };

  const renderItem = ({ item }) => (
    <View style={styles.card}>
      <View style={styles.userInfo}>
        <Text style={styles.nameText}>{item.name}</Text>
        <Text style={styles.emailText}>{item.email}</Text>
      </View>
      <View style={styles.buttonContainer}>
        <TouchableOpacity 
          style={[styles.button, styles.editButton]} 
          onPress={() => updateUser(item.id)}
        >
          <Text style={styles.buttonText}>Editar</Text>
        </TouchableOpacity>
        <TouchableOpacity 
          style={[styles.button, styles.deleteButton]} 
          onPress={() => confirmDelete(item.id, item.name)}
        >
          <Text style={styles.buttonText}>Deletar</Text>
        </TouchableOpacity>
      </View>
    </View>
  );

  return (
    <FlatList
      style={styles.listContainer}
      data={users}
      renderItem={renderItem}
      keyExtractor={(item) => item.id.toString()}
      contentContainerStyle={{ paddingBottom: 20 }} 
    />
  );
}

const styles = StyleSheet.create({
  listContainer: {
    flex: 1,
    paddingHorizontal: 10,
    marginTop: 10,
  },
  card: {
    backgroundColor: 'white',
    padding: 16,
    marginVertical: 8,
    borderRadius: 10,
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },
  userInfo: {
    flex: 1, 
  },
  nameText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#333',
  },
  emailText: {
    fontSize: 14,
    color: '#666',
  },
  buttonContainer: {
    flexDirection: 'row',
  },
  button: {
    marginLeft: 10,
    paddingVertical: 8,
    paddingHorizontal: 12,
    borderRadius: 5,
    justifyContent: 'center',
    alignItems: 'center',
  },
  editButton: {
    backgroundColor: '#4CAF50', // Verde
  },
  deleteButton: {
    backgroundColor: '#f44336', // Vermelho
  },
  buttonText: {
    color: 'white',
    fontWeight: 'bold',
    fontSize: 12,
  },
});