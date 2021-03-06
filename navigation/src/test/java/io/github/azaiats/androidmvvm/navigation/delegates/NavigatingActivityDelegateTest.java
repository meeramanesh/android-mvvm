/*
 * Copyright 2016 Andrei Zaiats.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.azaiats.androidmvvm.navigation.delegates;

import android.app.Activity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.github.azaiats.androidmvvm.core.common.BindingConfig;
import io.github.azaiats.androidmvvm.core.common.MvvmView;
import io.github.azaiats.androidmvvm.core.delegates.ActivityDelegateCallback;
import io.github.azaiats.androidmvvm.navigation.common.NavigatingViewModel;
import io.github.azaiats.androidmvvm.navigation.common.Navigator;
import io.github.azaiats.androidmvvm.testutils.ReflectionUtils;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Andrei Zaiats
 */
public class NavigatingActivityDelegateTest {

    @Mock
    private Navigator navigator;

    @Mock
    private ActivityDelegateCallback callback;

    @Mock
    private NavigatingDelegateCallback navigatingCallback;

    @Mock
    private NavigatingViewModel<Navigator> viewModel;

    @Mock
    private Activity delegatedActivity;

    @Mock
    private MvvmView view;

    @InjectMocks
    private TestNavigatingActivityDelegate delegate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        when(navigatingCallback.getNavigator()).thenReturn(navigator);
    }

    @Test
    public void testSetNavigatorOnCreate() {
        when(callback.getMvvmView()).thenReturn(view);
        when(callback.createViewModel()).thenReturn(viewModel);
        when(view.getBindingConfig()).thenReturn(new BindingConfig(0, 0));
        delegate.onCreate();
        verify(viewModel).setNavigator(navigator);
    }

    @Test
    public void testRemoveNavigatorOnDestroy() {
        ReflectionUtils.setParentField(delegate, "viewModel", viewModel);
        delegate.onDestroy();
        verify(viewModel).setNavigator(null);
    }

    @Test
    public void testNoNullPointerOnNavigatorDeleteIfViewModelRemoved() {
        assertNull(ReflectionUtils.getParentField(delegate, "viewModel"));
        delegate.onDestroy();
    }
}
