package com.ckx.runner.core.service.impl;

import com.ckx.runner.core.domain.Customer;
import com.ckx.runner.core.domain.Manager;
import com.ckx.runner.core.repository.ManagerRepository;
import com.ckx.runner.core.service.ManagerService;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {

    private ManagerRepository managerRepository;

    @Override
    public Manager login(String username, String password) {
        return managerRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public Manager get(Long id) {
        return managerRepository.getOne(id);
    }

    @Override
    public Manager modifyPwd(Long id, String newPwd) {
        Manager manager = managerRepository.getOne(id);
        manager.setPassword(newPwd);
        return managerRepository.save(manager);
    }
}
