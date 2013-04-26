package tests;

import net.thucydides.core.annotations.Feature;

/**
 * @author Alimenkou Mikalai
 * @version 1.0
 */
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

