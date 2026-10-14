import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule } from '@angular/forms';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { of } from 'rxjs';

import { RegistrationComponent } from './registration.component';
import { ClientService } from '../../services/client.service';

describe('RegistrationComponent', () => {
  let component: RegistrationComponent;
  let fixture: ComponentFixture<RegistrationComponent>;
  let clientService: jasmine.SpyObj<ClientService>;

  beforeEach(async () => {
    clientService = jasmine.createSpyObj<ClientService>('ClientService', ['saveClient', 'getClients']);
    clientService.getClients.and.returnValue(of([]));
    clientService.saveClient.and.returnValue(of({}));

    await TestBed.configureTestingModule({
      declarations: [ RegistrationComponent ],
      imports: [
        FormsModule,
        NoopAnimationsModule,
        HttpClientTestingModule,
        MatFormFieldModule,
        MatInputModule,
        MatButtonModule,
        MatTableModule
      ],
      providers: [{ provide: ClientService, useValue: clientService }]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not save when first name, last name or phone is empty', () => {
    spyOn(window, 'alert');

    component.firstName = 'John';
    component.lastName = '';
    component.phone = '555';

    component.onSave();

    expect(clientService.saveClient).not.toHaveBeenCalled();
    expect(window.alert).toHaveBeenCalled();
  });

  it('should not save when a value contains only whitespace', () => {
    spyOn(window, 'alert');

    component.firstName = '   ';
    component.lastName = 'Doe';
    component.phone = '555';

    component.onSave();

    expect(clientService.saveClient).not.toHaveBeenCalled();
    expect(window.alert).toHaveBeenCalled();
  });

  it('should save when first name, last name and phone are filled', () => {
    spyOn(window, 'alert');

    component.firstName = 'John';
    component.lastName = 'Doe';
    component.phone = '555';

    component.onSave();

    expect(clientService.saveClient).toHaveBeenCalledOnceWith(
      jasmine.objectContaining({ firstName: 'John', lastName: 'Doe', phone: '555' })
    );
  });

  it('should keep the Save button disabled while any field is empty', () => {
    const button: HTMLButtonElement = fixture.nativeElement.querySelector('button[type="submit"]');

    expect(button.disabled).toBeTrue();

    component.firstName = 'John';
    component.lastName = 'Doe';
    component.phone = '555';
    fixture.detectChanges();

    expect(button.disabled).toBeFalse();

    component.phone = '';
    fixture.detectChanges();

    expect(button.disabled).toBeTrue();
  });
});
