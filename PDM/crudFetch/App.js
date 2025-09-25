import { StatusBar } from 'expo-status-bar';
import { useEffect, useState } from 'react';
import { StyleSheet, Text, View, ActivityIndicator } from 'react-native';
// 1. Importe o SafeAreaProvider
import { SafeAreaProvider, SafeAreaView } from 'react-native-safe-area-context';
import UseForm from './componentes/UseForm';
import UseList from './componentes/UseList';

export default function App() {

  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(false);

  const fetchUsers = async () => {
    // ... (seu código para buscar usuários continua igual)
    try {
      setLoading(true);
      const response = await fetch(process.env.EXPO_PUBLIC_API_URL);
      const data = await response.json();
      setUsers(data);
    } catch (error) {
      console.log("Error fetching users:", error.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchUsers();
  }, []);

  return (
    // 2. Envolva tudo com o SafeAreaProvider
    <SafeAreaProvider>
      <SafeAreaView style={styles.safeArea}>
        <StatusBar style="dark" />
        <View style={styles.container}>
          <Text style={styles.title}>Lista de Usuários</Text>
          
          <UseForm onUserAdded={fetchUsers} />

          <View style={styles.listContainer}>
            {loading ? (
              <View style={styles.loadingContainer}>
                <ActivityIndicator size="large" color="#007BFF" />
              </View>
            ) : (
              <UseList users={users} onUserChanged={fetchUsers} />
            )}
          </View>
        </View>
      </SafeAreaView>
    </SafeAreaProvider>
  );
}

// Seus estilos continuam os mesmos
const styles = StyleSheet.create({
  safeArea: {
    flex: 1,
    backgroundColor: '#f0f2f5',
  },
  container: {
    flex: 1,
    paddingHorizontal: 15,
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
    color: '#1A202C',
    textAlign: 'center',
    marginTop: 20,
    marginBottom: 20,
  },
  listContainer: {
    flex: 1,
    width: '100%',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
});