package com.dev.marcellocamara.pgm.Contract;

/***
    marcellocamara@id.uff.br
            2019
***/

public interface ITaskListener {

    void OnSuccess();

    void OnError(String message);

}