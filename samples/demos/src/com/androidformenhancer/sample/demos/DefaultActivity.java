/*
 * Copyright 2012 Soichiro Kashima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.androidformenhancer.sample.demos;

import com.androidformenhancer.FormHelper;
import com.androidformenhancer.ValidationResult;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * @author Soichiro Kashima
 */
public class DefaultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);
    }

    public void onSubmit(View v) {
        FormHelper helper = new FormHelper(DefaultForm.class, this);
        ValidationResult result = helper.validate();
        if (result.hasError()) {
            Toast.makeText(this, result.getAllSerializedErrors(), Toast.LENGTH_SHORT).show();
        } else {
            // Create entity and do what you want
            // e.g. insert into database, send to server by HTTP
            DefaultEntity entity = helper.create(DefaultEntity.class);
            Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();
        }
    }

}
