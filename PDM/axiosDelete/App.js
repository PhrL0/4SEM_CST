import React,{useState, useEffect} from 'react';
import {View,Text,Button,FlatList,StyleSheet} from 'react-native'
import axios from 'axios';

export default function App(){
  const [users,setUsers] = useState([]);

  const fetchUsers = async()=>{
    try {
      const response = await axios.get(process.env.EXPO_PUBLIC_API_URL)

      if(response.status == 200){
        setUsers(response.data)
      } 
    } catch (e) {
      console.error("Erro ao fazer get", e.message)
    }
  }

  const deleteUser = async(id)=>{
    try {
      const response = await axios.delete(`${process.env.EXPO_PUBLIC_API_URL}/${id}`)
      
      if(response.status == 200){
        setUsers(currentUsers => currentUsers.filter((u) => u.id !== id));
      }
    } catch (e) {
      console.error("Error delete:", e.message)
    }
  }

  useEffect(()=>{
    fetchUsers();
  },[])

  return(
    <View style={styles.container}>
      <Text style={styles.title}>DELETE - Remover Usuário</Text>
      <FlatList
        data={users}
        keyExtractor={(item, index) => (item && item.id ? item.id.toString() : index.toString())}
        renderItem={({item}) =>(
          <View style={styles.userContainer}>
            <View style={styles.userInfo}>
              <Text style={styles.userName}>{item.name.firstname} {item.name.lastname}</Text>
              <Text style={styles.userEmail}>{item.email}</Text>
              <Text style={styles.userId}>ID: {item.id}</Text>
            </View>
              <Button title='Del' color="#e53e3e" onPress={()=>deleteUser(item.id)}></Button>
          </View>
        )}></FlatList>
    </View>
  )
}

const styles = StyleSheet.create({

  container: {
    flex: 1,
    paddingTop: 60, 
    paddingHorizontal: 20, 
    backgroundColor: '#f0f4f7', 
  },

  title: {
    fontSize: 24,
    fontWeight: 'bold',
    textAlign: 'center',
    marginBottom: 20, 
    color: '#333',
  },

  userContainer: {
    backgroundColor: '#ffffff', 
    padding: 16,
    borderRadius: 8, 
    marginBottom: 12, 
    flexDirection: 'row', 
    justifyContent: 'space-between', 
    alignItems: 'center', 
    shadowColor: '#000',
    shadowOffset: {
      width: 0,
      height: 2,
    },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 3,
  },

  userInfo: {
    flex: 1, 
    marginRight: 10, 
  },

  userName: {
    fontSize: 16,
    fontWeight: 'bold',
    color: '#444',
  },
  
  userEmail: {
    fontSize: 14,
    color: '#666',
    marginTop: 4,
  },
    
  userId: {
    fontSize: 12,
    color: '#999',
  }
});