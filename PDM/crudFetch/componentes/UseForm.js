import { useState } from "react";
import { Button, TextInput, View, StyleSheet } from "react-native";

export default function UseForm({ onUserAdded }) {
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const addUser = async () => {
    if (!name || !email) return;

    try {
      const response = await fetch(process.env.EXPO_PUBLIC_API_URL, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          name,
          email,
        }),
      });

      setName("");
      setEmail("");
      onUserAdded();
    } catch (e) {
      console.error("Error:", e.message);
    }
  };

  return (
    <View style={styles.form}>
      <TextInput
        style={styles.input}
        placeholder="Nome"
        value={name}
        onChangeText={setName}
      ></TextInput>
      <TextInput
        style={styles.input}
        placeholder="Email"
        value={email}
        onChangeText={setEmail}
        keyboardType="email-address"
        autoCapitalize="none"
      ></TextInput>
      <Button title="Adicionar Usuário" onPress={addUser}></Button>
    </View>
  );
}

const styles = StyleSheet.create({
  form: {
    margin: 20,
    padding: 20,
    backgroundColor: '#f7f7f7',
    borderRadius: 10,
  },
  input: {
    height: 40,
    borderColor: 'gray',
    borderWidth: 1,
    borderRadius: 5,
    marginBottom: 12,
    paddingHorizontal: 10,
    backgroundColor: '#fff',
  },
});