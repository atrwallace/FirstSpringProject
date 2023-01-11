package com.example.demo.controllers

import com.example.demo.entities.Contact
import com.example.demo.repository.ContactRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.persistence.EntityNotFoundException

@RestController
@RequestMapping("/contacts")
class ContactController {

    @Autowired
    lateinit var repository: ContactRepository

    @GetMapping
    fun index(): List<Contact> {

        return repository.findAll()
    }

    @PostMapping
    fun create(@RequestBody contact: Contact): Contact {

        return repository.save(contact)
    }

    @GetMapping("/{id}")
    fun show(@PathVariable("id") id: Long): Contact {
        return repository.findById(id).orElseThrow { EntityNotFoundException("Contato não encontrado") }
    }

    @PutMapping("/{id}")
    fun update(@PathVariable("id") id: Long, @RequestBody newContact: Contact): Contact {
        val contact = repository.findById(id).orElseThrow { EntityNotFoundException("Contato não encontrado") }

        contact.apply {
            this.name = newContact.name
            this.email = newContact.email
        }

        return repository.save(contact)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long) {
        val contact = repository.findById(id).orElseThrow { EntityNotFoundException("Contato não encontrado") }

        repository.delete(contact)

    }
}