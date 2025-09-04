import React, { useState } from 'react';
import { StyleSheet, Text, View, TouchableOpacity, Image, TextInput } from 'react-native';

export default function App() {
  const [pokemon, setPokemon] = useState(null);
  const [searchInput, setSearchInput] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  async function fetchPokemon() {
    if (!searchInput) {
      setError('Por favor, digite um nome de Pokémon.');
      return;
    }
    setLoading(true);
    setError(null);
    setPokemon(null);
    
    try {
      const response = await fetch(`https://pokeapi.co/api/v2/pokemon/${searchInput.toLowerCase()}`);
      
      if (!response.ok) {
        throw new Error('Pokémon não encontrado. Verifique o nome.');
      }

      const data = await response.json();
      setPokemon(data);
    } catch (e) {
      setError(e.message);
      console.error("Error GET:", e.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Pokédex</Text>
      
      <TextInput 
        style={styles.input}
        onChangeText={setSearchInput} 
        value={searchInput} 
        placeholder='Ex: pikachu'
        autoCapitalize='none'
      />
      
      <TouchableOpacity onPress={fetchPokemon} style={styles.button}>
        <Text style={styles.buttonText}>Buscar Pokémon</Text>
      </TouchableOpacity>
      
      {loading && <Text style={styles.loadingText}>Buscando...</Text>}
      {error && <Text style={styles.errorText}>{error}</Text>}
      
      {pokemon && (
        <View style={styles.pokemonCard}>
          <Text style={styles.pokemonName}>{pokemon.name}</Text>
          <Image
            style={styles.image}
            source={{ uri: pokemon.sprites.front_default }}
          />
          <View style={styles.detailsContainer}>
            <Text style={styles.detailText}>ID: {pokemon.id}</Text>
            <Text style={styles.detailText}>Experiência Base: {pokemon.base_experience}</Text>
            <Text style={styles.detailText}>Peso: {pokemon.weight} lbs</Text>
            <Text style={styles.detailText}>Altura: {pokemon.height} ft</Text>
            <Text style={styles.detailText}>Tipo: {pokemon.types.map(t => t.type.name).join(', ')}</Text>
          </View>
        </View>
      )}
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#E6E6FA',
    alignItems: 'center',
    paddingTop: 40,
  },
  title: {
    fontSize: 32,
    fontWeight: '800',
    color: '#3B5998',
    marginBottom: 20,
    textShadowColor: 'rgba(0, 0, 0, 0.1)',
    textShadowOffset: { width: 1, height: 1 },
    textShadowRadius: 2,
  },
  input: {
    width: '80%',
    height: 50,
    backgroundColor: '#FFFFFF',
    borderRadius: 25,
    paddingHorizontal: 20,
    fontSize: 16,
    color: '#333333',
    marginBottom: 20,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
    elevation: 5,
  },
  button: {
    backgroundColor: '#FF6B6B',
    borderRadius: 25,
    paddingVertical: 15,
    paddingHorizontal: 40,
    marginBottom: 30,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.2,
    shadowRadius: 6,
    elevation: 8,
  },
  buttonText: {
    color: '#FFFFFF',
    fontSize: 18,
    fontWeight: '700',
  },
  loadingText: {
    fontSize: 16,
    color: '#777',
    marginBottom: 20,
  },
  errorText: {
    fontSize: 16,
    color: '#D8000C',
    backgroundColor: '#FFD2D2',
    padding: 10,
    borderRadius: 10,
    marginBottom: 20,
  },
  pokemonCard: {
    backgroundColor: '#FFFFFF',
    borderRadius: 20,
    padding: 25,
    marginHorizontal: 20,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 4 },
    shadowOpacity: 0.1,
    shadowRadius: 8,
    elevation: 10,
    alignItems: 'center',
  },
  pokemonName: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333333',
    textTransform: 'capitalize',
    marginBottom: 10,
  },
  image: {
    width: 200,
    height: 200,
    resizeMode: 'contain',
    marginBottom: 15,
  },
  detailsContainer: {
    alignSelf: 'flex-start',
    width: '100%',
  },
  detailText: {
    fontSize: 16,
    color: '#555555',
    marginBottom: 5,
  },
});
