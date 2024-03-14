package com.epam.engx.task;

import com.epam.engx.task.thirdpartyjar.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import org.mockito.Mockito;


import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


public class PrintTest {
    private View view;
    private DatabaseManager manager;
    private Command command;
    private final DataSet dataSet = new DataSetImpl();

    @Before
    public void setup() {
        manager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
        command = new Print(view, manager);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldTrowExceptionWhenCommandIsWrong() {
        command.process("print");
    }

    @Test
    public void shouldProcessValidCommand() {
        //when
        boolean canProcess = command.canProcess("print test");
        //then
        assertTrue(canProcess);
    }

    @Test
    public void shouldNotProcessInvalidCommand() {
        //when
        boolean canProcess = command.canProcess("qwe");
        //then
        assertFalse(canProcess);
    }

    @Test
    public void shouldPrintTableWithMultiDataSets() {
        //given
        createUserDataSets(createUser(1, "Steven Seagal", "123456"), createUser(2, "Eva Song", "789456"));
        //when
        command.process("print users");
        //then
        assertPrinted("[" +
                "╔════════════════╦════════════════╦════════════════╗\n" +
                "║       id       ║      name      ║    password    ║\n" +
                "╠════════════════╬════════════════╬════════════════╣\n" +
                "║       1        ║ Steven Seagal  ║     123456     ║\n" +
                "╠════════════════╬════════════════╬════════════════╣\n" +
                "║       2        ║    Eva Song    ║     789456     ║\n" +
                "╚════════════════╩════════════════╩════════════════╝\n" + "]");
    }

    private void createUserDataSets(DataSet... users) {
        List<DataSet> dataSets = new LinkedList<>();
        Collections.addAll(dataSets, users);
        Mockito.when(manager.getTableData("users")).thenReturn(dataSets);
    }

    private DataSet createUser(int id, String name, String password) {
        DataSet user = new DataSetImpl();
        user.put("id", id);
        user.put("name", name);
        user.put("password", password);
        return user;
    }

    private void prepareSingleResult() {
        List<DataSet> dataSets = new LinkedList<>();
        dataSets.add(dataSet);
        Mockito.when(manager.getTableData("test")).thenReturn(dataSets);
    }


    private void assertPrinted(String expected) {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals(expected, captor.getAllValues().toString());
    }
}