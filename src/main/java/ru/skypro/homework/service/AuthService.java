package ru.skypro.homework.service;

import ru.skypro.homework.dto.RegReq;
import ru.skypro.homework.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegReq regReq, Role role);
}
