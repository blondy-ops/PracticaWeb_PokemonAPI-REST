package com.example.PokemonApi.service;

import com.example.PokemonApi.excepciones.ResourceNotFoundException;
import com.example.PokemonApi.model.Pokemon;
import com.example.PokemonApi.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {
    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }


    // Obtener todos los Pokémon
    public List<Pokemon> obtenerTodos() {
        return pokemonRepository.findAll();
    }

    // Obtener Pokémon por ID
    public Pokemon obtenerPorId(Long id) {
        return pokemonRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No se encontro el pokemon"));
    }

    // Crear un Pokémon
    public Pokemon crearPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    // Actualizar completamente (PUT)
    public Pokemon actualizarPokemonCompleto(
            Long id, Pokemon pokemonActualizado) {

        return pokemonRepository.findById(id).map(pokemon -> {
            pokemon.setNombre(pokemonActualizado.getNombre());
            pokemon.setTipo(pokemonActualizado.getTipo());
            pokemon.setNivel(pokemonActualizado.getNivel());
            pokemon.setAtaque(pokemonActualizado.getAtaque());
            pokemon.setDefensa(pokemonActualizado.getDefensa());
            pokemon.setDescripcion(pokemonActualizado.getDescripcion());
            pokemon.setImagenUrl(pokemonActualizado.getImagenUrl());

            pokemon.setFechaCaptura(pokemonActualizado.getFechaCaptura());

            return pokemonRepository.save(pokemon);
        }).orElseThrow(() -> new ResourceNotFoundException("No se pudo actualizar el pokemon"));
    }

    // Actualizar parcialmente (PATCH)
    public Pokemon actualizarPokemonParcial(
            Long id, Pokemon pokemonParcial) {

        return pokemonRepository.findById(id).map(pokemon -> {

            if (pokemonParcial.getNombre() != null)
                pokemon.setNombre(pokemonParcial.getNombre());

            if (pokemonParcial.getTipo() != null)
                pokemon.setTipo(pokemonParcial.getTipo());

            if (pokemonParcial.getNivel() != null)
                pokemon.setNivel(pokemonParcial.getNivel());

            if (pokemonParcial.getAtaque() != null)
                pokemon.setAtaque(pokemonParcial.getAtaque());

            if (pokemonParcial.getDefensa() != null)
                pokemon.setDefensa(pokemonParcial.getDefensa());

            if (pokemonParcial.getDescripcion() != null)
                pokemon.setDescripcion(pokemonParcial.getDescripcion());

            if (pokemonParcial.getImagenUrl() != null)
                pokemon.setImagenUrl(pokemonParcial.getImagenUrl());

            if(pokemonParcial.getFechaCaptura() != null)
                pokemon.setFechaCaptura((pokemonParcial.getFechaCaptura()));

            return pokemonRepository.save(pokemon);
        }).orElseThrow(() -> new ResourceNotFoundException("No se puudo actualizar el pokemon"));
    }

    // Eliminar un Pokémon
    public void eliminarPokemon(Long id) {
        pokemonRepository.deleteById(id);
    }
}
