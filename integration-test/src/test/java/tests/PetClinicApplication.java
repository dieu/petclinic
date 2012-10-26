package tests;

import net.thucydides.core.annotations.Feature;

public class PetClinicApplication {
    @Feature
    public class PetOwnerManagement {
        public class ViewInformationPertainingToPetOwner {}
        public class UpdateTheInformationPertainingToPetOwner {}
        public class AddNewPetOwnerToTheSystem {}
    }
    @Feature
    public class PetManagement {
        public class ViewInformationPertainingToPet {}
        public class UpdateTheInformationPertainingToPet {}
        public class AddNewPetToTheSystem {}
    }
    @Feature
    public class VeterinarianVisits {
        public class ViewListOfVeterinariansAndTheirSpecialities {}
        public class ViewInformationPertainingToPetVisitationHistory {}
        public class AddInformationPertainingToVisitToThePetVisitationHistory {}
    }
}

