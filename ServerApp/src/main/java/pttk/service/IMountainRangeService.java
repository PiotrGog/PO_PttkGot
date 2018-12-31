package pttk.service;

import pttk.model.MountainRange;

import java.util.List;

public interface IMountainRangeService {
    public void addMountainRage(MountainRange p);
    public void updateMountainRange(MountainRange p);
    public List<MountainRange> listMountainRange();
    public MountainRange getMountainRangeById(int id);
    public void removeMountainRange(int id);
}
