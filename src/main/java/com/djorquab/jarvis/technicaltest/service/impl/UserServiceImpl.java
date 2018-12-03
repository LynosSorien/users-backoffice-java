package com.djorquab.jarvis.technicaltest.service.impl;

import com.djorquab.jarvis.technicaltest.ApplicationConstants;
import com.djorquab.jarvis.technicaltest.commons.Location;
import com.djorquab.jarvis.technicaltest.commons.Name;
import com.djorquab.jarvis.technicaltest.commons.Picture;
import com.djorquab.jarvis.technicaltest.dto.UserApi;
import com.djorquab.jarvis.technicaltest.dto.UserApiResponse;
import com.djorquab.jarvis.technicaltest.dto.UserDTO;
import com.djorquab.jarvis.technicaltest.dto.UserEditor;
import com.djorquab.jarvis.technicaltest.manager.PropertyManager;
import com.djorquab.jarvis.technicaltest.mappers.UsersMapper;
import com.djorquab.jarvis.technicaltest.model.Users;
import com.djorquab.jarvis.technicaltest.repository.UsersRepository;
import com.djorquab.jarvis.technicaltest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UsersMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository repository;

    @Autowired
    private PropertyManager propertyManager;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDTO getUserByEmail(String email) {
        return mapper.entityToDTO(repository.findByEmail(email));
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        return mapper.entityToDTO(repository.findByUsername(username));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void loadUsers(int numberOfUsers, boolean cleanDatabase) {
        if (cleanDatabase) {
            repository.deleteByUsernameNot(propertyManager.getAdminUsername());
        }
        ResponseEntity<UserApiResponse> response = restTemplate.getForEntity(propertyManager.getUserApi()+numberOfUsers, UserApiResponse.class);
        if (response.hasBody()) {
            decodeUserApiResponse(response.getBody());
        }
    }

    @Override
    public List<UserDTO> findAll() {
        return mapper.entitiesToDTOs(repository.findAll());
    }

    @Override
    public void deleteByEmail(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public void deleteByUsername(String username) {
        repository.deleteByUsername(username);
    }

    @Override
    public List<UserDTO> findUsersByEmailLike(String email) {
        List<UserDTO> users = mapper.entitiesToDTOs(repository.findByEmailLike(email));
        return fillUserDTOData(users);
    }

    @Override
    public List<UserDTO> findUsersByUsernameLike(String username) {
        List<UserDTO> users = mapper.entitiesToDTOs(repository.findByUsernameLike(username));
        return fillUserDTOData(users);
    }

    @Override
    public UserEditor dtoToEditor(UserDTO dto) {
        UserEditor.UserEditorBuilder builder = UserEditor.builder();
        builder.username(dto.getUsername());
        builder.email(dto.getEmail());
        builder.cell(dto.getCell());
        builder.phone(dto.getPhone());
        builder.gender(dto.getGender());
        if (dto.getName() != null) {
            builder.title(dto.getName().getTitle());
            builder.firstName(dto.getName().getFirst());
            builder.lastName(dto.getName().getLast());
        }
        if (dto.getLocation() != null) {
            builder.street(dto.getLocation().getStreet());
            builder.city(dto.getLocation().getCity());
            builder.state(dto.getLocation().getState());
            builder.postcode(dto.getLocation().getPostcode());
        }
        if (dto.getPicture() != null) {
            builder.imageLargeUrl(dto.getPicture().getLarge());
            builder.imageMediumUrl(dto.getPicture().getMedium());
            builder.imageThumbnailUrl(dto.getPicture().getThumbnail());
        }
        return builder.build();
    }

    @Override
    public void updateUser(UserEditor userEditor) {
        Users user = repository.findByUsername(userEditor.getUsername());
        if (userEditor.getPassword() != null && !"".equals(userEditor.getPassword())) {
            user.setPassword(passwordEncoder.encode(userEditor.getPassword()));
            user.setDecodedPassword(userEditor.getPassword());
        }
        user.setEmail(userEditor.getEmail());
        user.setCell(userEditor.getCell());
        user.setPhone(userEditor.getPhone());
        user.setGender(userEditor.getGender());

        if (checkNullProperty(userEditor.getTitle())
                && checkNullProperty(userEditor.getFirstName())
                && checkNullProperty(userEditor.getLastName())) {
            user.setName(null);
        } else {
            Name name = user.getName();
            if (name == null) {
                name = new Name();
            }
            name.setTitle(userEditor.getTitle());
            name.setFirst(userEditor.getFirstName());
            name.setLast(userEditor.getLastName());
            user.setName(name);
        }

        if (checkNullProperty(userEditor.getCity())
                && checkNullProperty(userEditor.getStreet())
                && checkNullProperty(userEditor.getCity())
                && checkNullProperty(userEditor.getPostcode())) {
            user.setLocation(null);
        } else {
            Location location = user.getLocation();
            if (location == null) {
                location = new Location();
            }
            location.setCity(userEditor.getCity());
            location.setPostcode(userEditor.getPostcode());
            location.setState(userEditor.getState());
            location.setStreet(userEditor.getStreet());
            user.setLocation(location);
        }

        if (checkNullProperty(userEditor.getImageLargeUrl())
                && checkNullProperty(userEditor.getImageMediumUrl())
                && checkNullProperty(userEditor.getImageThumbnailUrl())) {
            user.setPicture(null);
        } else {
            Picture picture = user.getPicture();
            if (picture == null) {
                picture = new Picture();
            }
            picture.setLarge(userEditor.getImageLargeUrl());
            picture.setMedium(userEditor.getImageMediumUrl());
            picture.setThumbnail(userEditor.getImageThumbnailUrl());
            user.setPicture(picture);
        }

        repository.save(user);
    }

    private boolean checkNullProperty(String value) {
        return value == null || "".equals(value);
    }

    private List<UserDTO> fillUserDTOData(List<UserDTO> users) {
        for (UserDTO user : users) {
            if (user.getName() != null) {
                user.setFullname(user.getName().getFirst()+" "+user.getName().getLast());
            }
        }
        return users;
    }

    private void decodeUserApiResponse(UserApiResponse response) {
        if (response.getResults() != null) {
            for (UserApi userApi : response.getResults()) {
                Users user = Users.builder()
                        .cell(userApi.getCell())
                        .email(userApi.getEmail())
                        .username(userApi.getLogin().getUsername())
                        .password(passwordEncoder.encode(userApi.getLogin().getPassword()))
                        .gender(userApi.getGender())
                        .location(userApi.getLocation())
                        .picture(userApi.getPicture())
                        .phone(userApi.getPhone())
                        .name(userApi.getName())
                        .roles(ApplicationConstants.USER_ROLE)
                        .decodedPassword(userApi.getLogin().getPassword())
                        .build();
                repository.save(user);
            }
        }
    }
}
